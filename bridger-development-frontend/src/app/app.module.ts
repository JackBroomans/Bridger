import { NgModule } from '@angular/core';
// import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
// import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AddUserAccountComponent } from './components/add-user-account/add-user-account.component';
import { BridgerDetailsComponent } from './components/user-account-details/user-account-details.component';
import { BridgerListComponent } from './components/user-account-list/user-account-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddUserAccountComponent,
    BridgerDetailsComponent,
    BridgerListComponent
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
