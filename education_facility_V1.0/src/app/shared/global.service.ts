import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './modal/User.modal';

@Injectable({
	providedIn: 'root'
})
export class GlobalService 
{
	user: User = new User();
	requestHeader: HttpHeaders;
	isAuthenticated: Boolean = false;

	constructor() { }

	setRequestHeader(header: HttpHeaders) {
        this.requestHeader = header;
    }

    getRequestHeader() : HttpHeaders {
        return this.requestHeader;
    }

    getUser(): User {
        return this.user;
    }
}
