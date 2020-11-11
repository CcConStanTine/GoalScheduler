import testImage from '../images/planner.jpg';

class Database {
    authenticated: boolean;
    userLogin: string;
    password: string;
    userID: number;
    tokenID: string;
    userName: string;
    userPhoto: any;


    constructor() {
        this.authenticated = false;
        this.userLogin = "admin";
        this.password = "admin";
        this.userID = 4;
        this.tokenID = "asdasdasdsa";
        this.userName = "Konrad Dulęba";
        this.userPhoto = testImage;
    }

    getUserInfo = () => {
        return {
            userID: this.userID,
            tokenID: this.tokenID,
            userName: this.userName,
            userPhoto: this.userPhoto,
        }
    }

    createAccount = (nickname: string, password: string, name: string, email: string) => {
        return this.login(nickname, password);
    }

    login = (nickname: string, password: string) => {
        if (this.userLogin === nickname && this.password === password) {
            this.authenticated = true;

            return {
                key: 200,
                message: 'Successfully',
                userInfo: this.getUserInfo()
            }
        }

        return {
            key: 500,
            message: 'Login failed',
        }
    }

    signout(): void {
        this.authenticated = false;
    }

    isAuthenticated = () => this.authenticated;
}

export default new Database();