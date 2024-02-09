import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { PollService } from '../_services/poll.service';
import { AppGlobals } from '../global/global-config'


@Component({
  selector: 'view-polls',
  templateUrl: './veiw-poll.component.html',
  styleUrls: ['./veiw-poll.component.css']
})

//created the mock data in json to reflect in ui
export class ViewPollsComponent implements OnInit {

    constructor(private pollService : PollService, private appGlobals: AppGlobals, private router: Router){}
    errorMessage = '';
   viewPollMock : any[]= [
    {
        title: "title 1",
        startDate: "2022-11-10",
        endDate: "2022-11-10",
    }, {
        title: "title 2",
        startDate: "2022-11-10",
        endDate: "2022-11-10",
    }, {
        title: "title 3",
        startDate: "2022-11-10",
        endDate: "2022-11-10",
    }, {
        title: "title 3",
        startDate: "2022-11-10",
        endDate: "2022-11-10",
    },

];

update(pollId: any) {
  this.router.navigate(['/edit-poll/' + pollId]);
}

addQuestionare(pollId:any){
  this.router.navigate(['/list-questions/' + pollId]);
}
//delete the pollid 
delete(pollId: any) {
    this.pollService.delete(pollId).subscribe({
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
          this.listPoll();
        }
    })
}
//function to list the total polls created
listPoll() {
    this.pollService.list(this.appGlobals.loginUserDetail.loginId).subscribe({
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