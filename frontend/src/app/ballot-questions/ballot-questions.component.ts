import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NavigationExtras, Params, Router, ActivatedRoute } from '@angular/router';
import { VoterService } from '../_services/voter.service';
import { AppGlobals } from '../global/global-config';
import { PollQuestionsService } from '../_services/poll-questions.service';


@Component({
  selector: 'app-ballot-questions',
  templateUrl: './ballot-questions.component.html',
  styleUrls: ['./ballot-questions.component.css']
})
export class BallotQuestionsComponent implements OnInit {

  questions:any =[];
  pollId: any;
  voters: any = [];
  partyId:any = null;
  errorMessage = '';
  isAddVoterFailed = false;
  isAddVoterSuccess = false;
  successMessage = '';

  constructor(private pollQuestionService : PollQuestionsService, private appGlobals: AppGlobals, private router: Router, 
    private route: ActivatedRoute, private voterService: VoterService){}

    // Routing to ModifyQuestionsComponent
  addQuestions(){
    this.router.navigate([`/modify-questions/`+this.pollId+`/`+null])
  }
//To delete the selected question respective to the PollId
  delete(pollQuestionId: any){
    this.pollQuestionService.delete(pollQuestionId).subscribe({
      next: (res) => {
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
        this.listPollQuestions();
      }
  })
  }

  //navigating to update the questions- ModifyQuestionsComponent
  update(pollQuestionId:any) {
    this.router.navigate([`/modify-questions/`+this.pollId+`/`+pollQuestionId])
  }
// To list the Existing questions with respect ot selected PollId
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

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => this.pollId = params['pollId']);
    this.listPollQuestions();
  }
//Adding voter to the particular PollId
  addVoter(prtyId: any){
    this.isAddVoterFailed = false;
    this.isAddVoterSuccess = false
    let partyForm: any = new FormGroup({
      partyId: new FormControl(this.partyId)
    })
    this.voterService.addVoter(partyForm.value, this.pollId).subscribe({
      next: (res) => {
        console.log('next-------',res);
        if(res) {
          this.isAddVoterSuccess = true;
          this.successMessage = 'User add as Voter for this poll'
        }
      },
      error: (err) => {
        console.log("err-----", err)
        this.isAddVoterFailed = true;
        if(err.error.message) {
          this.errorMessage = err.error.message
        } else {
          this.errorMessage = err.error.errorDefinition.message;
        }
      },
      complete: () =>{
        this.voters = [];
      }
    })
    console.log(this.partyId)
  }
//To get the list of voters with respect to the POllId
  getVoters() {
    console.log("in getVoter")
    this.voterService.listVoter(this.appGlobals.loginUserDetail.loginId).subscribe({
      next: (res) => {
          this.voters = [];
        console.log('next-------',res);
        if(res) {
          this.voters = res;
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
        
      }
    })
  }


  // addQuestions(){
  //   let navigationExtras: NavigationExtras = {
  //     state: {
  //       data:""
  //     }
  //   }
  //   this.router.navigate([`/modify-questions/12`] , navigationExtras)
  // }
  // delete(pollQuestionId: any){

  // }
  // update(pollQuestionId:any){

  // }

  // modifyQuestions(list:any){
  //   let navigationExtras: NavigationExtras = {
  //     state: {
  //       data:list
  //     }
  //   }
  //   this.router.navigate([`/modify-questions/12`] , navigationExtras)
  // }
  
}