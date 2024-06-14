package com.zelianko.weatherapp.presentation.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.zelianko.weatherapp.domain.entity.City
import com.zelianko.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.zelianko.weatherapp.domain.usecase.SearchCityUseCase
import com.zelianko.weatherapp.presentation.search.SearchStore.Intent
import com.zelianko.weatherapp.presentation.search.SearchStore.Label
import com.zelianko.weatherapp.presentation.search.SearchStore.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class ChangeSearchQuery(val query: String) : Intent
        data object ClickBack : Intent

        data object ClickSearch : Intent
        data class ClickCity(val city: City) : Intent
    }

    data class State(
        val searchQuery: String,
        val searchState: SearchState
    ) {

        sealed interface SearchState {

            data object Initial : SearchState
            data object Loading : SearchState
            data object Error : SearchState
            data object EmptyResult : SearchState

            data class SuccessLoaded(val cities: List<City>) : SearchState

        }
    }

    sealed interface Label {

        data object ClickBack : Label

        data object SaveToFavourite : Label

        data class OpenForecast(val city: City) : Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase
) {
    //В метод create передается причина открытия экрана
    fun create(openReason: OpenReason): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                searchQuery = "",
                searchState = State.SearchState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(openReason) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
        data class ChangeSearchQuery(val query: String) : Msg
        data object LoadingSearchResult : Msg
        data object SearchResultError : Msg
        data class SearchResultLoaded(val cities: List<City>) : Msg


    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    //В конструктор передаем причину открытия
    private inner class ExecutorImpl(
        private val openReason: OpenReason
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private var searchJob: Job? = null
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeSearchQuery -> {
                    dispatch(Msg.ChangeSearchQuery(intent.query))
                }

                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                is Intent.ClickCity -> {
                    when (openReason) {
                        OpenReason.AddToFavourite -> {
                            scope.launch {
                                changeFavouriteStateUseCase.addToFavourite(intent.city)
                                publish(Label.SaveToFavourite)
                            }
                        }

                        OpenReason.RegularSearch -> {
                            publish(Label.OpenForecast(intent.city))
                        }
                    }

                }

                Intent.ClickSearch -> {
                    //Если нескольколько раз коикать и запрос не завершился, сначало отменяется предыдущий запрос и стартует новый
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        dispatch(Msg.LoadingSearchResult)
                        try {
                            val cities = searchCityUseCase(getState().searchQuery)
                            dispatch(Msg.SearchResultLoaded(cities))
                        } catch (e: Exception) {
                            dispatch(Msg.SearchResultError)
                        }
                    }
                }
            }
        }
//        Так как экшинов нет, можно его закоментировать
//        override fun executeAction(action: Action, getState: () -> State) {
//        }
    }

    //В зависимости от переданного сообщения происходит логика выполения
    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeSearchQuery -> {
                copy(searchQuery = msg.query)
            }

            Msg.LoadingSearchResult -> {
                 copy(searchState = State.SearchState.Loading)
            }

            Msg.SearchResultError -> {
                copy(searchState = State.SearchState.Error)
            }

            //Два варианта, может прийти кол-ция с городами, а может пустая, если городов не нашли
            is Msg.SearchResultLoaded -> {
                   val searchState = if (msg.cities.isEmpty()) {
                       State.SearchState.EmptyResult
                   } else {
                       State.SearchState.SuccessLoaded(msg.cities)
                   }
                copy(
                    searchState = searchState
                )
            }
        }
    }
}
