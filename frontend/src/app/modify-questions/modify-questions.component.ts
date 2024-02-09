import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationStart, Params, Router } from '@angular/router';
import { PollQuestionsService } from '../_services/poll-questions.service';

@Component({
  selector: 'app-modify-questions',
  templateUrl: './modify-questions.component.html',
  styleUrls: ['./modify-questions.component.css']
})
export class ModifyQuestionsComponent implements OnInit {
  data: any;
  QuestionData: any;
  OptionsData: any;
  pollId: any;
  pollQuestionId: any;
  errorMessage = '';
  isPollQuestionUpdateFailed = false;

  modifyQuestionForm: any = new FormGroup({
    question:new FormControl(),
    pollQuestionId: new FormControl(),
    options:this.option()
    
  });
  constructor(public activatedRoute: ActivatedRoute, public router: Router,private fb:FormBuilder, private route: ActivatedRoute, 
    private pollQuestionsService: PollQuestionsService) {
   }

   getPollQuestion() {
    this.pollQuestionsService.get(this.pollQuestionId).subscribe({
      next: (res) => {
        console.log('next-------',res);
        if(res) {
          this.setPollQuestionForm(res);
        }
      },
      error: (err) => {
        console.log("err-----", err)
        this.isPollQuestionUpdateFailed = true;
        if(err.error.message) {
          this.errorMessage = err.error.message
        } else {
          this.errorMessage = err.error.errorDefinition.message;
        }
      },
      complete: () =>{
        console.log("modifyQuestionForm-------", this.modifyQuestionForm)
      }
    })
  }

  setPollQuestionForm(pollQuestion: any) {
    this.modifyQuestionForm = new FormGroup({
      question:new FormControl(pollQuestion.question),
      pollQuestionId: new FormControl(pollQuestion.pollQuestionId),
      options:
        this.setOptions(pollQuestion.options)
      
    });
  }

  setOptions(options: any[]) {
    let optionList: FormArray = this.fb.array([]);
    for(let i=0; i<options.length; i++) {
      optionList.push(this.setOption(options[i]));
    }
    return optionList;
  }

  setOption(option: any) {
    return this.fb.group({
      option: new FormControl(option.option),
      correct: new FormControl(option.correct),
      allowedResponseOptionId: new FormControl(option.allowedResponseOptionId)
    })
  }

  ngOnInit(): void {
    console.log("sdvs");
    this.route.params.subscribe((params: Params) => this.pollId = params['pollId']);
    this.route.params.subscribe((params: Params) => this.pollQuestionId = params['pollQuestionId']);
    console.log("this.pollQuestionId-------", this.pollQuestionId)
    if(this.pollQuestionId != 'null') {
      this.getPollQuestion();
    }
  }

  option() {
    let optionList: FormArray = this.fb.array([]);
    optionList.push(this.emptyOption())
    optionList.push(this.emptyOption())
    return optionList
  }

  emptyOption() {
    return this.fb.group({
      option: ['', Validators.required],
      correct: ['']
    });
  }

  onClear() {
    this.modifyQuestionForm.reset()
    this.router.navigate(['/list-questions/' + this.pollId])
  }

  onSubmit() {
    console.log("data in submit---------", this.modifyQuestionForm.controls['pollQuestionId'].value)
    if(this.modifyQuestionForm.controls['pollQuestionId'].value == undefined || this.modifyQuestionForm.controls['pollQuestionId'].value == null ) {
      this.pollQuestionsService.create(this.modifyQuestionForm.value, this.pollId).subscribe({
        next: (res) => {
          console.log('next-------',res);
          if(res) {
            this.router.navigate(['/list-questions/' + this.pollId])
          }
        },
        error: (err) => {
          console.log("err-----", err)
          this.isPollQuestionUpdateFailed = true;
          if(err.error.message) {
            this.errorMessage = err.error.message
          } else {
            this.errorMessage = err.error.errorDefinition.message;
          }
        },
        complete: () =>{
          this.modifyQuestionForm.reset()
        }
      })
    } else {
      this.pollQuestionsService.update(this.modifyQuestionForm.value, this.modifyQuestionForm.controls['pollQuestionId'].value).subscribe({
        next: (res) => {
          console.log('next-------',res);
          if(res) {
            // this.router.navigate(['/menu']);
            this.router.navigate(['/list-questions/' + this.pollId])
          }
        },
        error: (err) => {
          console.log("err-----", err)
          this.isPollQuestionUpdateFailed = true;
          if(err.error.message) {
            this.errorMessage = err.error.message
          } else {
            this.errorMessage = err.error.errorDefinition.message;
          }
        },
        complete: () =>{
          this.modifyQuestionForm.reset()
        }
      })
    }
  }
  }

