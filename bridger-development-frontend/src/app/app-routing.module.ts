import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserAccountListComponent } from './components/user-account-list/user-account-list.component';
import { UserAccountDetailsComponent} from './components/user-account-details/user-account-details.component';
import { AddUserAccountComponent } from './components/add-user-account/add-user-account.component';

const routes: Routes = [
  { path: '', redirectTo: 'user-account', pathMatch: 'full' },
  { path: 'user-account', component: UserAccountListComponent },
  { path: 'user-account/:id', component: UserAccountDetailsComponent },
  { path: 'add', component: AddUserAccountComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
