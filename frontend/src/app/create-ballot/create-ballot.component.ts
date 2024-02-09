import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { PollService } from '../_services/poll.service'
import { AppGlobals } from '../global/global-config'

@Component({
  selector: 'app-create-ballot',
  templateUrl: './create-ballot.component.html',
  styleUrls: ['./create-ballot.component.css']
})
export class CreateBallotComponent implements OnInit {
  balletform: any = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    startDate: new FormControl(''),
    endDate: new FormControl('')
  })

  errorMessage = '';
  isPollCreationFailed = false;

  constructor(private fb: FormBuilder, private router: Router, private pollService: PollService,
      private appGlobals: AppGlobals) { }

  ngOnInit(): void {
    this.formBuilder();
  }
//Creation of Form for the Ballot
  formBuilder() {
    const { title, description, startDate, endDate } = this.balletform;
    return (this.fb.group({
      title: title,
      description: description,
      startDate: startDate,
      endDate: endDate
    }));
  }
//Clear the details onclick of Cancel
  onClear(e: any) {
    if (e) e.preventDefault()
    this.balletform.reset({
      "title": "",
      "description": "",
      "startDate": "",
      "endDate": "",
    })
    this.router.navigate(['/menu']);
  }
  //Log the created Ballot and on successful creation navigate to main menu
  onSubmit() {
    this.pollService.create(this.balletform.value, this.appGlobals.loginUserDetail.loginId).subscribe({
      next: (res) => {
        console.log('next-------',res);
        if(res) {
          this.router.navigate(['/menu']);
        }
      },
      error: (err) => {
        console.log("err-----", err)
        this.isPollCreationFailed = true;
        if(err.error.message) {
          this.errorMessage = err.error.message
        } else {
          this.errorMessage = err.error.errorDefinition.message;
        }
      },
      complete: () =>{
        this.balletform.reset()
      }
    })
  }

}
