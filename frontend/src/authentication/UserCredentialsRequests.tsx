import { registerUser, loginUser } from '../utils/interfaces';
import {
    RequestsMethods,
    UserSignIn,
    UserSignUp,
    GetUserInfo,
    GetUserPhoto,
    PostUserPhoto,
    ReturnToken,
} from '../utils/requestsInterfaces';
import AppConfig from './AppConfig';

class UserCredentialsRequests extends AppConfig {
    private getLocalStorageAndSetConstructorValues = (): void => {
        const { token, id, username } = JSON.parse(localStorage.getItem('user')!);

        this.setUserValues = { token, id, username };
    }

    private getUserValue = (value: string): string => `/user/${this.getUserId}/${value}`;

    public deleteUserPhoto = () =>
        this.handleRequests(RequestsMethods.DELETE, this.getUserValue('image'));

    public changeUserPassword = (password: string) =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('password'), { password });

    public getCurrentUserInfo = (): Promise<GetUserInfo> =>
        this.handleRequests(RequestsMethods.GET, this.getUserValue('info'));

    public getUserPhoto = (): Promise<GetUserPhoto> =>
        this.handleRequests(RequestsMethods.GET, this.getUserValue('image'));

    public register = (data: registerUser): Promise<UserSignUp> =>
        this.handleRequests(RequestsMethods.POST, 'sign-up', data);

    public login = (data: loginUser): Promise<UserSignIn> =>
        this.handleRequests(RequestsMethods.POST, 'sign-in', data);

    public checkIfPasswordIsCorrect = (password: string): Promise<UserSignIn> =>
        this.handleRequests(RequestsMethods.POST, 'sign-in', { username: this.getUserame, password });

    public changeUserPhoto = (file: FormData): Promise<PostUserPhoto> =>
        this.handleRequests(RequestsMethods.POST, this.getUserValue('image'), file);

    public changeUsername = (firstName: string, lastName: string, nick: string): Promise<GetUserInfo> =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('basic'), { firstName, lastName, nick });

    public changeUserEmail = (email: string): Promise<GetUserInfo> =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('email'), { email });

    public setLocalStorageValues = (data: UserSignIn): UserSignIn => {
        if (data.token)
            localStorage.setItem('user', JSON.stringify(data));

        return {
            ...data
        };
    }

    public getCurrentUser = (): ReturnToken => {
        if (localStorage.getItem('user')) {
            this.getLocalStorageAndSetConstructorValues();
        }

        return { token: this.getToken }
    }

    public logout = (): void => localStorage.removeItem("user");

}

export default UserCredentialsRequests;