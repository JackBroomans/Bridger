import { NgModule } from '@angular/core';
// import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { UserAccountAddComponent } from './components/user-account-add/user-account-add.component';
import {
  UserAccountDetailsComponent
} from './components/user-account-details/user-account-details.component';
import {
  UserAccountListComponent
} from './components/user-account-list/user-account-list.component';

@NgModule({
  declarations: [
    AppComponent,
    UserAccountAddComponent,
    UserAccountDetailsComponent,
    UserAccountListComponent
  ],
  imports: [
    // BrowserModule,
    // AppRoutingModule
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
