import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyQuestionsComponent } from './modify-questions.component';

describe('ModifyQuestionsComponent', () => {
  let component: ModifyQuestionsComponent;
  let fixture: ComponentFixture<ModifyQuestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyQuestionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
