import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { mergeMap} from 'rxjs/operators'


const AUTH_API = 'http://localhost:2900/voting/pollQuestion/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class PollQuestionsService {
    constructor(private http: HttpClient) { }
    
    list(pollId: any): Observable<any> {
      return this.http.get(AUTH_API + `list/${pollId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
                return of(v)
          } }))
    }   
    
    delete(pollQuestionId: any): Observable<any> {
      return this.http.delete(AUTH_API + `delete/question/${pollQuestionId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return ('v is null');  
          } else {
                return of(v)
          } }))
    }

    create(pollQuestionForm:any, pollId: any): Observable<any> {
      return this.http.post(AUTH_API + `create/${pollId}`, JSON.stringify(pollQuestionForm), httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
               return of(v)
          } }))
    }

    get(pollQuestionId: any): Observable<any> {
      return this.http.get(AUTH_API + `get/${pollQuestionId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return ('v is null');  
          } else {
                return of(v)
          } }))
    }

    update(pollQuestionForm:any, pollQuestionId: any): Observable<any> {
      return this.http.patch(AUTH_API + `update/question/${pollQuestionId}`, JSON.stringify(pollQuestionForm), httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return ('v is null');  
          } else {
               return of(v)
          } }))
    }

    
}