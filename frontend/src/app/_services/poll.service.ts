import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { mergeMap} from 'rxjs/operators'


const AUTH_API = 'http://localhost:2900/voting/poll/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class PollService {
    constructor(private http: HttpClient) { }

    create(pollForm:any, loginId: any): Observable<any> {
        return this.http.post(AUTH_API + `create/${loginId}`, JSON.stringify(pollForm), httpOptions).
          pipe(mergeMap(v=> {
            console.log("in service-----", v)
            if(v === null){
              return throwError('v is null');  
            } else {
                 return of(v)
            } }))
      }
    
    list(loginId: any): Observable<any> {
      return this.http.get(AUTH_API + `list/${loginId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
                return of(v)
          } }))
    }

    get(pollId: any): Observable<any> {
      return this.http.get(AUTH_API + `get/${pollId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
                return of(v)
          } }))
    }

    delete(pollId: any): Observable<any> {
      return this.http.delete(AUTH_API + `delete/${pollId}`, httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
                return of(v)
          } }))
    }

    update(pollForm:any, pollId: any): Observable<any> {
      return this.http.patch(AUTH_API + `update/${pollId}`, JSON.stringify(pollForm), httpOptions).
        pipe(mergeMap(v=> {
          console.log("in service-----", v)
          if(v === null){
            return throwError('v is null');  
          } else {
               return of(v)
          } }))
    }
}