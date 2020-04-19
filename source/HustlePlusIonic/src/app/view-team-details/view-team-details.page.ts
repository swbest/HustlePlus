import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController } from '@ionic/angular';

import { TeamService } from '../team.service';
import { Team } from '../team';

@Component({
  selector: 'app-view-team-details',
  templateUrl: './view-team-details.page.html',
  styleUrls: ['./view-team-details.page.scss'],
})
export class ViewTeamDetailsPage implements OnInit {

  teamId: number;
  teamToView: Team;
  retrieveTeamError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private teamService: TeamService,
    public alertController: AlertController) {
    this.retrieveTeamError = false;
    this.error = false;
    this.resultSuccess = false;
  }

  ngOnInit() {
    this.teamId = parseInt(this.activatedRoute.snapshot.paramMap.get('teamId'));
    this.refreshTeam();
  }

  ionViewWillEnter() {
    this.refreshTeam();
  }

  refreshTeam() {
    this.teamService.getTeamByTeamId(this.teamId).subscribe(
      response => {
        this.teamToView = response.team;
      },
      error => {
        this.retrieveTeamError = true;
        console.log('********** ViewTeamDetailsPage.ts: ' + error);
      }
    );
  }

  back() {
    this.router.navigate(["/viewMyTeams"]);
  }
}
