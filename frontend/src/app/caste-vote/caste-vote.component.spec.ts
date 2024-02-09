import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CasteVoteComponent } from './caste-vote.component';

describe('CasteVoteComponent', () => {
  let component: CasteVoteComponent;
  let fixture: ComponentFixture<CasteVoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CasteVoteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CasteVoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
