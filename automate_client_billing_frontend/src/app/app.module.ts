import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CardComponentComponent } from './card-component/card-component.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatSliderModule } from '@angular/material/slider';
import {MatExpansionModule} from '@angular/material/expansion';
import { RightPanelComponent } from './right-panel/right-panel.component';
import { ExpansionCardInfoComponent } from './expansion-card-info/expansion-card-info.component';
import { ExpansionCardComponent } from './expansion-card/expansion-card.component';
import { ExpansionPopCardInfoComponent } from './expansion-pop-card-info/expansion-pop-card-info.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EmployeeInfoPopUpComponent } from './employee-info-pop-up/employee-info-pop-up.component';
import { ServiceClassService } from './service-class.service';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ClientPaymentComponent } from './client-payment/client-payment.component';
import { MatTab, MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { ClientApprovedComponent } from './client-approved/client-approved.component';
import { ClientPendingComponent } from './client-pending/client-pending.component';
import { ClientRejectedComponent } from './client-rejected/client-rejected.component';
import { FormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Ng2SearchPipeModule} from 'ng2-search-filter';
import { LeadResponseComponent } from './lead-response/lead-response.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { NgxSpinnerModule } from 'ngx-spinner';
import {ReactiveFormsModule} from '@angular/forms';



/* Login Page Library for Authentication*/

import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import {
  GoogleLoginProvider,
} from 'angularx-social-login';
import { LeadResponseApprovedComponent } from './lead-response-approved/lead-response-approved.component';
import { LeadResponsePendingComponent } from './lead-response-pending/lead-response-pending.component';
import { LeadResponseRejectedComponent } from './lead-response-rejected/lead-response-rejected.component';
import { NotificationComponent } from './notification/notification.component';
import { AllDetailsComponent } from './all-details/all-details.component';
import { PaymentComponent } from './payment/payment.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ConfigEmpInfoComponent } from './config-emp-info/config-emp-info.component';
import { TempConfigComponent } from './temp-config/temp-config.component';
import { TabelHeadingComponent } from './tabel-heading/tabel-heading.component';
import { ManagerViewComponent } from './manager-view/manager-view.component';

/* Login Page Library for Authentication*/

const routesA : Routes = [
  {path: 'billingDetails',component:RightPanelComponent},
  {path:'home',component:HomePageComponent},
  {path: 'client',component:ClientPaymentComponent},
  {path:'response/:id/:month/:year',component:AllDetailsComponent},
  {path:'client/:id/:month/:year',component:AllDetailsComponent},
  {path: 'Notification',component:NotificationComponent},
  {path: 'finalPayment/:id/:month/:year',component:PaymentComponent},
  {path: 'response',component:LeadResponseComponent},
  {path:'manager/:id/:month/:year',component:ManagerViewComponent},
  {path: 'config/:id/:month/:year',component:TempConfigComponent},
  {path: '',component:LoginComponent},
  // {path:'**',redirectTo:'home'},
];

const googleLoginOptions = {
  scope: 'profile email',
  cookiepolicy: 'single_host_origin'
}

// const envtest= process;

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SideNavComponent,
    HomePageComponent,
    CardComponentComponent,
    RightPanelComponent,
    ExpansionCardInfoComponent,
    ExpansionCardComponent,
    ExpansionPopCardInfoComponent,
    EmployeeInfoPopUpComponent,
    LoginComponent,
    ClientPaymentComponent,
    ClientApprovedComponent,
    ClientPendingComponent,
    ClientRejectedComponent,
    LeadResponseComponent,
    LeadResponseApprovedComponent,
    LeadResponsePendingComponent,
    LeadResponseRejectedComponent,
    NotificationComponent,
    AllDetailsComponent,
    PaymentComponent,
    ConfigEmpInfoComponent,
    TempConfigComponent,
    TabelHeadingComponent,
    ManagerViewComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatSliderModule,
    MatExpansionModule,
    MatDialogModule,
    RouterModule.forRoot(routesA),
    MatTabsModule,
    MatCardModule,
    FormsModule,
    HttpClientModule,
    Ng2SearchPipeModule,
    NgxPaginationModule,
    NgxSpinnerModule,
    ReactiveFormsModule,
    SocialLoginModule //Module For Login Authentication
  ],
  providers: [
    /* Login Authentication Provider */
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: true,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '1001993542608-a8al66fd131s0k9p6aqclo7bt6qet6ur.apps.googleusercontent.com',googleLoginOptions
            ),
            //
          },
        ],
      } as SocialAuthServiceConfig,
    },
    /* Login Authentication Provider */
    ServiceClassService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
