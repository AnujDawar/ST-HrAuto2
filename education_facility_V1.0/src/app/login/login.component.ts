import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalService } from '../shared/global.service';
import { URLS } from '../shared/urls';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	focus;
	focus1;
	username: string;
	password: string;
	showLoginError = "";

	constructor(private http: HttpClient, 
		private globalService: GlobalService, 
		private router: Router,
		private spinner: NgxSpinnerService) { }

	ngOnInit() {
		this.showLoginError = "";
		// this.spinner.show();
	}

	onSubmit() 
	{
		this.spinner.show();

		console.log("on submit");

		console.log(this.username);
		console.log(this.password);

		this.http.post(URLS.AUTHENTICATE,
			{ "username": this.username, "password": this.password },
			{ headers: this.globalService.getRequestHeader() }).subscribe(res => {

				console.log(res);

				if(!res["id"])
				{
					this.globalService.isAuthenticated = false;
					this.showLoginError = 'This portal is currently available for ST Employees only.';
					this.username = "";
					this.password = "";
					return;
				}

				if (res['responseError']['errorStatus'] && res['responseError']['errorStatus'] == 'NOT OK') {
					this.globalService.isAuthenticated = false;
					this.showLoginError = 'This portal is currently available for ST Employees only.';
					this.username = "";
					this.password = "";
					return;
				}

				this.showLoginError = "";

				this.globalService.user.id = res['id'];
				this.globalService.user.name = res['name'];
				this.globalService.user.firstName = res['firstName'];
				this.globalService.user.lastName = res['lastName'];
				this.globalService.user.email = res['email'];
				this.globalService.user.costcenter = res['costcenter'];
				this.globalService.user.workLocationId = res['workLocationId'];
				this.globalService.user.homeLocationId = res['homeLocationId'];
				this.globalService.user.supervisorId = res['supervisorId'];

				let headers = new HttpHeaders().set('token', res['accessToken']);
				let newHeader = headers.append('employeeId', res['id']);

				this.globalService.setRequestHeader(newHeader);
				this.globalService.isAuthenticated = true;

				this.router.navigate(['/education-facility-approval-form']);
				this.spinner.hide();
			},
			err => {
				console.log(err);
				this.showLoginError = 'Invalid Credentials';
				this.username = "";
				this.password = "";
				
				this.spinner.hide();
			}
			);
	}
}