import { Component, OnInit, Injectable } from '@angular/core';
import { EducationFacilityApproval } from '../../shared/modal/education-facility-approval-model';
import { URLS } from 'src/app/shared/urls';
import { DynamicGrid } from '../dynamic-grid-file-upload';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GlobalService } from 'src/app/shared/global.service';
import { Router } from '@angular/router';

@Component({
	selector: 'app-education-facility-approval-form',
	templateUrl: './education-facility-approval-form.component.html',
	styleUrls: ['./education-facility-approval-form.component.css']
})
@Injectable({
	providedIn: 'root'
})
export class EducationFacilityApprovalFormComponent implements OnInit {
	model = new EducationFacilityApproval();
	fileToUpload: File;
	filesToUploadList: File[] = [];
	attachmentArray: Array<DynamicGrid> = [];
	deletedAttachmentArray: Array<DynamicGrid> = [];
	newAttachment: DynamicGrid;
	retrievedFile: any;
	base64Data: any;
	retrieveResonse: any;
	USER_ID: string = "";
	closeResult: string;
	showModal: string;
	modalMessage: string;
	showBackdrop: boolean;

	constructor(private http: HttpClient, private globalService: GlobalService, private router: Router) {
	}

	ngOnInit(): void {

		this.showBackdrop = true;

		if(!this.globalService.isAuthenticated)
		{
			this.router.navigate(['/login']);
			return;	
		}

		this.http.get(URLS.EDUCATION_FACILITY_APPROVAL_HEALTH_CHECK, { headers: this.globalService.getRequestHeader() }).subscribe(
			data => {
				console.log("App is up");
				this.getCurrentRequestDetails();
			},
			err => 
			{
				console.log(err);
				this.showBackdrop = true;
				this.modalMessage = "Error Connecting to the server. Please try again later";
				this.showModal = 'block';
			}
		);
	}

	getCurrentRequestDetails() {

		this.USER_ID = this.globalService.user.id;

		this.http.get(URLS.EDUCATION_FACILITY_APPROVAL_GET_CURRENT_REQUEST_DETAILS + this.USER_ID, { headers: this.globalService.getRequestHeader() }).subscribe(
			data => {
				console.log(data);

				if (data['dateOfJoining'])
					delete data['dateOfJoining'].sqlFormat;

				if (data['dateOfJoiningOfCourse'])
					delete data['dateOfJoiningOfCourse'].sqlFormat;

				if (data['availedPastCouseStartDate'])
					delete data['availedPastCouseStartDate'].sqlFormat;

				if (data['availedPastCouseEndDate'])
					delete data['availedPastCouseEndDate'].sqlFormat;

				console.log(data);

				this.model.requestId = data['requestId'];
				this.model.mobile = data['mobile'];
				this.model.approvedAmount = data['approvedAmount'];
				this.model.availedPastCourseName = data['availedPastCourseName'];
				this.model.availedPastCouseEndDate = data['availedPastCouseEndDate'];
				this.model.availedPastCouseStartDate = data['availedPastCouseStartDate'];
				this.model.costOfProgram = data['costOfProgram'];
				this.model.dateOfJoining = data['dateOfJoining'];
				this.model.dateOfJoiningOfCourse = data['dateOfJoiningOfCourse'];
				this.model.department = data['department'];
				this.model.designation = data['designation'];
				this.model.employeeId = data['employeeId'];
				this.model.employeeName = data['employeeName'];
				this.model.estimatedDurationOfCourse = data['estimatedDurationOfCourse'];
				this.model.howProgrmContributes = data['howProgrmContributes'];
				this.model.hrResponsible = data['hrResponsible'];
				this.model.isAgree = data['isAgree'];
				this.model.isPartOfDevelopmentPlan = data['isPartOfDevelopmentPlan'];
				this.model.nameOfInstitution = data['nameOfInstitution'];
				this.model.isPartOfDevelopmentPlan = data['partOfDevelopmentPlan'];
				this.model.programTitle = data['programTitle'];
				this.model.totalCost = data['totalCost'];
				this.model.attachments = data['attachments'];

				this.model.attachments.forEach(element => {
					this.newAttachment = new DynamicGrid(element['attachmentId'], element['fileName']);
					this.attachmentArray.push(this.newAttachment);
				});
				this.showBackdrop = false;
			},
			err => {
				console.log(err);
				this.showBackdrop = false;
			}
		);
	}

	getCurrentModel() {
		return JSON.stringify(this.model);
	}

	onSubmit() {
		this.showBackdrop = true;

		console.log(this.model);

		let headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*').append('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,DELETE,PUT');

		let formData: FormData = new FormData();

		console.log(this.filesToUploadList);
		this.removeDups();
		console.log(this.filesToUploadList);

		this.setAttachmentIdsForDeactivation();

		for (let i = 0; i < this.filesToUploadList.length; i++) {
			console.log("attachment" + +(i + 1));
			formData.append("attachment" + +(i + 1), this.filesToUploadList[i], this.filesToUploadList[i].name);
		}

		formData.append("body", JSON.stringify(this.model));

		this.http.post(URLS.EDUCATION_FACILITY_APPROVAL_FORM_SUBMIT, formData, { observe: 'response', headers: this.globalService.getRequestHeader() })
			.subscribe((response) => {
				if (response.status === 200) {
					console.log('Image uploaded successfully');
					this.modalMessage = "Request Submitted Successfully";
					this.showModal = 'block';
				}
				else {
					console.log('Image not uploaded successfully');
					this.modalMessage = "Error submitting the request. Please try again later.";
					this.showModal = 'block';
				}
			});
	}

	handleFileInput(event) {
		console.log(event.target.files[0]);
		this.fileToUpload = event.target.files[0];
		this.filesToUploadList.push(this.fileToUpload);
	}

	addRow(index) {
		if (this.attachmentArray.length >= 5)
			return;

		this.newAttachment = new DynamicGrid(0, "");
		this.attachmentArray.push(this.newAttachment);
		console.log(this.attachmentArray);
		return true;
	}

	deleteRow(index) {
		if (this.attachmentArray.length == 1) {
			let attachment = this.attachmentArray.splice(index, 1)[0];
			this.deletedAttachmentArray.push(attachment);
			this.newAttachment = new DynamicGrid(0, "");
			this.attachmentArray.push(this.newAttachment);
			this.filesToUploadList = [];
			return false;
		} else {
			let attachment = this.attachmentArray.splice(index, 1)[0];
			this.deletedAttachmentArray.push(attachment);
			this.filesToUploadList.splice(index, 1);
			return true;
		}
	}

	removeDups() {
		var obj = {};

		for (var i = 0, len = this.filesToUploadList.length; i < len; i++)
			obj[this.filesToUploadList[i]['name']] = this.filesToUploadList[i];

		this.filesToUploadList = new Array();

		for (var key in obj)
			this.filesToUploadList.push(obj[key]);
	}

	onCloseHandler() {
		this.showBackdrop = false;
		this.showModal = 'none';
	}

	checkFiles() {
		console.log(this.deletedAttachmentArray);
		console.log(this.attachmentArray);
		console.log(this.filesToUploadList);
	}

	setAttachmentIdsForDeactivation() {

		let attachmentIds = "";

		this.deletedAttachmentArray.forEach(element => {
			attachmentIds += element.attachmentId + ",";
		});

		if (attachmentIds.length > 0) {
			attachmentIds = attachmentIds.substring(0, attachmentIds.length - 1);
			console.log(attachmentIds);
		}

		this.model.attachmentIdsToRemove = attachmentIds;
	}
}
