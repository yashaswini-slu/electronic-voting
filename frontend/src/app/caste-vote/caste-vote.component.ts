import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PollService } from '../_services/poll.service';
import { AppGlobals } from '../global/global-config'
import { VoterService } from '../_services/voter.service';


@Component({
  selector: 'app-caste-vote',
  templateUrl: './caste-vote.component.html',
  styleUrls: ['./caste-vote.component.css']
})
export class CasteVoteComponent implements OnInit {

  constructor(private voterService : VoterService, private appGlobals: AppGlobals, private router: Router){}
    errorMessage = '';
   viewPollMock : any[]= [];
   
//Navigate to CasteVoteComponent
vote(pollId:any){
  this.router.navigate(['/casteVote/' + pollId]);
}
//Listing the total polls eligible to Cate vote
listPoll() {
  this.voterService.listPoll(this.appGlobals.loginUserDetail.loginId).subscribe({
      next: (res) => {
          this.viewPollMock = [];
        console.log('next-------',res);
        if(res) {
          this.viewPollMock = res;
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

  ngOnInit(): void {
    this.listPoll();
  }

}
