class Auth {
    authenticated: Boolean;

    constructor() {
        this.authenticated = false;
    }

    login(callback: any): void {
        this.authenticated = true;
        callback();
    }

    signout(callback: any): void {
        this.authenticated = false;
        callback();
    }

    isAuthenticated = () => this.authenticated;
}

export default new Auth();