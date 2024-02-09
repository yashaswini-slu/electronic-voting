import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Params, Router } from '@angular/router';
import { PollQuestionsService } from '../_services/poll-questions.service';
import { VoterService } from '../_services/voter.service';
import { AppGlobals } from '../global/global-config';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css']
})
export class VoteComponent implements OnInit {

  questions:any =[];
  pollId: any;
  errorMessage = '';
  constructor(public router: Router, private route: ActivatedRoute, private pollQuestionService: PollQuestionsService, 
    private voterService: VoterService, private appGlobals: AppGlobals) { }

  listPollQuestions() {
    this.pollQuestionService.list(this.pollId).subscribe({
        next: (res) => {
            this.questions = [];
          console.log('next-------',res);
          if(res) {
            this.questions = res;
          }
        },
        error: (err) => {
          console.log("err-----", err)
          if(err.error.message) {
            this.errorMessage = err.error.message
          } else {
            this.errorMessage = err.error.errorDefinition.message;
          }
        },
        complete: () =>{}
      })
}

setId(index:any, optionId:any) {
  this.questions[index].responseOptionId = optionId
  console.log("this.questions[index].responseOptionId----", this.questions[index])
 
}

onSubmit() {
  this.voterService.castVoter(this.questions, this.appGlobals.loginUserDetail.loginId, this.pollId).subscribe({
    next: (res) => {
        this.questions = [];
      console.log('next-------',res);
      if(res) {
        this.questions = res;
      }
    },
    error: (err) => {
      console.log("err-----", err)
      if(err.error.message) {
        this.errorMessage = err.error.message
      } else {
        this.errorMessage = err.error.errorDefinition.message;
      }
    },
    complete: () =>{
      this.router.navigate(['/menu']);
    }
  })
}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => this.pollId = params['pollId']);
    this.listPollQuestions();
  }
}
