import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { TeamService } from '../team.service';
import { Team } from '../team';
import { SessionService } from '../session.service';
import { Student } from '../student';

@Component({
  selector: 'app-create-new-team',
  templateUrl: './create-new-team.page.html',
  styleUrls: ['./create-new-team.page.scss'],
})
export class CreateNewTeamPage implements OnInit {

  submitted: boolean;
  newTeam: Team;
  infoMessage: string;
  errorMessage: string;
  hasError: boolean;

  constructor(private teamService: TeamService,
    private router: Router,
    private sessionService: SessionService) {
    this.submitted = false;
    this.newTeam = new Team();
  }

  ngOnInit() {
    // this.newTeam.students.push(this.sessionService.getCurrentStudent());
  }

  clear() {
    this.submitted = false;
    this.newTeam = new Team();
  }

  create(createTeamForm: NgForm) {
    this.submitted = true;

    if (createTeamForm.valid) {
      this.teamService.createNewTeam(this.newTeam).subscribe(
        response => {
          this.infoMessage = 'New team created ' + response.newTeamId;
          this.errorMessage = null;
          this.hasError = false;
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
          this.hasError = true;
        }
      );
    }
  }

  back() {
    if (!this.hasError) {
      this.router.navigate(["/teams"]);
    }
  }
}

