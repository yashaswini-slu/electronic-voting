import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers:[AuthService]
    });
    service = TestBed.inject(AuthService);
  });

 
  it('should sigup for application', () => {
    const signupform = {"userName":"ui_test@aaaa.com","firstName":"ui-test","lastName":"ui-test","password":"12345","confirmPassword":"12345"}
    service.register(signupform);
    expect(service).toBeTruthy();
  });

  it('should login for application', () => {
    const loginform = {"userName":"ui_test@aaaa.com","password":"12345"}
    service.register(loginform);
    expect(service).toBeTruthy();
  });

});