import axios from 'axios';
import { AUTH_LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE } from './ActionTypes';

const server_url = '';

/* Register */

//thunk => register start
export function registerRequest(user_id, user_pw) {
    return (dispatch) => {
        dispatch(register());

        return axios.post(server_url, { user_id, user_pw })
            .then((response) => {
                dispatch(registerSuccess());
            }).catch((result) => {
                dispatch(registerFailure(result.response.result));
            });
    };
}

export function register() {
    return {
        type: AUTH_LOGIN
    };
}

export function registerSuccess() {
    return {
        type: LOGIN_SUCCESS,
    };
}

export function registerFailure(error) {
    return {
        type: LOGIN_FAILURE,
        result
    };
}
