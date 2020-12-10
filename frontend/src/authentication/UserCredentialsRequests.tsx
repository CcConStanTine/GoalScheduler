import { registerUser, loginUser } from '../utils/interfaces';
import { UserSignIn, RequestsMethods } from '../utils/requestsInterfaces';
import AppConfig from './AppConfig';

class UserCredentialsRequests extends AppConfig {
    private getLocalStorageAndSetConstructorValues = () => {
        const { token, id, username } = JSON.parse(localStorage.getItem('user')!);

        this.token = token;
        this.userId = id;
        this.username = username;
    }

    private getUserValue = (value: string) => `/user/${this.userId}/${value}`;

    public deleteUserPhoto = () => this.handleRequests(this.getUserValue('image'), RequestsMethods.DELETE);

    public getUserPhoto = () => this.handleRequests(this.getUserValue('image'), RequestsMethods.GET);

    public changeUserPhoto = (file: FormData) => this.handleRequests(this.getUserValue('image'), RequestsMethods.POST, file);

    public changeUsername = (firstName: string, lastName: string, nick: string,) => this.handleRequests(this.getUserValue('basic'), RequestsMethods.PATCH, { firstName, lastName, nick });

    public changeUserEmail = (email: string) => this.handleRequests(this.getUserValue('email'), RequestsMethods.PATCH, { email });

    public changeUserPassword = (password: string) => this.handleRequests(this.getUserValue('password'), RequestsMethods.PATCH, { password });

    public checkIfPasswordIsCorrect = (password: string) => this.handleRequests('sign-in', RequestsMethods.POST, { username: this.username, password });

    public getCurrentUserInfo = () => this.handleRequests(this.getUserValue('info'), RequestsMethods.GET);

    public register = (data: registerUser) => this.handleRequests('sign-up', RequestsMethods.POST, data);

    public login = (data: loginUser): Promise<UserSignIn> => this.handleRequests('sign-in', RequestsMethods.POST, data);

    public setLocalStorageValues = (data: any) => {
        if (data.token)
            localStorage.setItem('user', JSON.stringify(data));

        return {
            ...data
        };
    }

    public getCurrentUser = () => {
        if (localStorage.getItem('user')) {
            this.getLocalStorageAndSetConstructorValues();
        }

        return { token: this.token }
    }

    public logout = () => localStorage.removeItem("user");

}

export default UserCredentialsRequests;