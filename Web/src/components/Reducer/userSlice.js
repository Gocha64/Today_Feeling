import { createSlice } from "@reduxjs/toolkit";

export const userSlice = createSlice({
    name: "user",
    initialState: {
        id: "",
        sex: 1,
        name: "",
        email: "",
        anger: "",
        fear: "",
        happiness: "",
        sadness: "",
        surprise: "",
        isLogin: null,
    },
    reducers: {
        //login 성공
        userLogIn: (state, action) => {
            state.id = action.payload.id;
            state.sex = action.payload.sex;
            state.name = action.payload.username;
            state.email = action.payload.email;
            state.anger = action.payload.anger;
            state.fear = action.payload.fear;
            state.happiness = action.payload.happiness;
            state.sadness = action.payload.sadness;
            state.surprise = action.payload.surprise;
            return state;
        },

        //로그인 실패
        userLogOut: (state) => {
            state.id = "";
            state.sex = 1;
            state.name = "";
            state.email = "";
            state.anger = "";
            state.fear = "";
            state.happiness = "";
            state.sadness = "";
            state.surprise = "";
            return state;
        },
    },
});

export const { userLogIn, userLogOut } = userSlice.actions;
export default userSlice.reducer;