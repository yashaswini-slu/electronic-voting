import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { PollService } from './poll.service';

describe('PollService', () => {
  let service: PollService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers:[PollService]
    });
    service = TestBed.inject(PollService);
  });

 
  it('should create poll for application', () => {
    const pollForm = {"title": "ui-test-tile", "description": "ui-test-description", startDate:"2022-11-19", endDate:"2022-11-29"}
    service.create(pollForm, 1);
    expect(service).toBeTruthy
  });

});