import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
 //Added navigation to edit,create,caste and results components
  createBallot(): void {
    console.log('createBallot')
    this.router.navigate(['/createBallot']);
  }

  casteVote():void{
    console.log('CasteVote')
    this.router.navigate(['/casteVote']);
  }
  results():void{
    console.log('CasteVote')
    this.router.navigate(['/results']);
  }


  viewPolls(): void {
    this.router.navigate(['/view-poll']);
  }

}
