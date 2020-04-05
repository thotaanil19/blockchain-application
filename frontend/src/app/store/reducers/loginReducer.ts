export interface LoginReducerState {
    isUserLoggedIn: boolean;
}
export const initialState: LoginReducerState = {isUserLoggedIn: false};

export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOGOUT = 'LOGOUT';

export function loginReducer(state = initialState, action) : LoginReducerState {
    switch (action.type) {
        case LOGIN_SUCCESS :
            return {...state, isUserLoggedIn: true};
        case LOGIN_FAILURE :
            return {...state, isUserLoggedIn: false};
        case LOGOUT :
            return {...state, isUserLoggedIn: false}
        default : 
        return state;

    }
} 