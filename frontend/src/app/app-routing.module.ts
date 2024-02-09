import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { CreateBallotComponent } from './create-ballot/create-ballot.component'
import { ViewPollsComponent } from './veiw-poll/view-poll.component';
import { BrowserModule } from '@angular/platform-browser';
import { EditPollComponent } from './edit-poll/edit-poll.component';
import { BallotQuestionsComponent } from './ballot-questions/ballot-questions.component';
import { ModifyQuestionsComponent } from './modify-questions/modify-questions.component';
import { CasteVoteComponent } from './caste-vote/caste-vote.component';
import { VoteComponent } from './vote/vote.component';
import { ResultsComponent } from './results/results.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'menu', component: MainMenuComponent },
  { path: 'createBallot', component: CreateBallotComponent },
  { path: 'view-poll', component: ViewPollsComponent },
  { path: 'edit-poll/:pollId', component: EditPollComponent},
  { path : 'list-questions/:pollId', component: BallotQuestionsComponent},
  { path : 'modify-questions/:pollId/:pollQuestionId', component: ModifyQuestionsComponent},
  { path : 'casteVote', component: CasteVoteComponent},
  { path : 'casteVote/:pollId', component: VoteComponent},
  { path : 'results', component: ResultsComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), BrowserModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
