import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app.routing';

import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { LandingComponent } from './landing/landing.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';

import { HomeModule } from './home/home.module';
import { LoginComponent } from './login/login.component';
import { EducationFacilityComponent } from './education-facility/education-facility.component';
import { EducationFacilityApprovalFormComponent } from './education-facility/education-facility-approval-form/education-facility-approval-form.component';
import { HttpClientModule } from '@angular/common/http';
import { GlobalService } from './shared/global.service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { MenuScreenLevelOneComponent } from './menu-screen-level-one/menu-screen-level-one.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LandingComponent,
    ProfileComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    EducationFacilityComponent,
    EducationFacilityApprovalFormComponent,
    MenuScreenLevelOneComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    HomeModule,
    HttpClientModule,
    NgxSpinnerModule
  ],
  providers: [GlobalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
