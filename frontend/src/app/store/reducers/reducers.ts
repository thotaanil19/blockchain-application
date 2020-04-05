import {loginReducer, LoginReducerState} from './loginReducer'
import { ActionReducerMap } from '@ngrx/store';

interface AppState {
    loginReducer: LoginReducerState
}

export const reducers: ActionReducerMap<AppState> = {
    loginReducer: loginReducer
};