import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { CreateBallotComponent } from './create-ballot/create-ballot.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { ViewPollsComponent } from './veiw-poll/view-poll.component';
import { EditPollComponent } from './edit-poll/edit-poll.component';
import { AppGlobals } from './global/global-config';
import { BallotQuestionsComponent } from './ballot-questions/ballot-questions.component';
import { ModifyQuestionsComponent } from './modify-questions/modify-questions.component';
import { CasteVoteComponent } from './caste-vote/caste-vote.component';
import { VoteComponent } from './vote/vote.component';
import { ResultsComponent } from './results/results.component';
import { NgChartsModule } from 'ng2-charts';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    CreateBallotComponent,
    MainMenuComponent,
    ViewPollsComponent,
    EditPollComponent,
    BallotQuestionsComponent,
    ModifyQuestionsComponent,
    CasteVoteComponent,
    VoteComponent,
    ResultsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgChartsModule
  ],
  providers: [authInterceptorProviders, AppGlobals],
  bootstrap: [AppComponent]
})
export class AppModule { }