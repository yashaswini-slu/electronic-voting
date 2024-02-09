import { Component, OnInit } from '@angular/core';
import { AppGlobals } from '../global/global-config'
import { VoterService } from '../_services/voter.service'

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  datasets: any[] = [];
  labels: any[] = [];
  result: any;
  errorMessage = ''
  constructor(private appGlobals: AppGlobals, private voterService: VoterService) {}
  
  getResult() {
    this.voterService.voteResult(this.appGlobals.loginUserDetail.loginId).subscribe({
      next: (res) => {
        if(res) {
          let transformedlabel = Object.keys(res).map(key => res[key])
          let transformedData = Object.keys(res).map(key => key)
          let label: any[] = [];
          let data: any[] = [];
          console.log("this.result-------", this.result)
          for (let key of transformedData.values()) {
            label.push(key)
          }
  
          for (let value of transformedlabel.values()) {
            data.push(value)
          }
      this.datasets = [
        {
          label: 'Poll',
          data: data,
        },
      ];
    
      this.labels = label;
    
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
    this.getResult();
    }
  }


