import { Injectable } from "@angular/core";

@Injectable()
export class AppGlobals {
    public loginUserDetail: any = null;

    resetGlobalValues() {
        this.loginUserDetail = null;
    }
}