import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from '../session.service';
import { TeamService } from '../team.service';
import { Team } from '../team';

@Component({
  selector: 'app-view-my-teams',
  templateUrl: './view-my-teams.page.html',
  styleUrls: ['./view-my-teams.page.scss'],
})
export class ViewMyTeamsPage implements OnInit {

	teams: Team[];
	errorMessage: string;
	searchQuery: string = '';

	constructor(private router: Router, private teamService: TeamService, private sessionService: SessionService) { }

	ngOnInit() {
		this.refreshTeams();
	}

	ionViewWillEnter() {
		this.refreshTeams();
	}

	refreshTeams() {
		this.teamService.getTeamByStudentId(this.sessionService.getCurrentStudent().userId).subscribe(
			response => {
				this.teams = response.teams
			},
			error => {
				this.errorMessage = error
			}
		);
	}

	getSearchTeams(ev: any) {
		// Reset items back to all of the items
		//		this.refreshStudents();

		// set val to the value of the searchbar
		const val = ev.target.value;

		// if the value is an empty string don't filter the items
		if (val && val.trim() != '') {
			this.teams = this.teams.filter((team) => {
				return (team.teamName.toLowerCase().indexOf(val.toLowerCase()) > -1);
			})
		}
	}

	onCancel(ev: any) {
		// Reset items back to all of the items
		this.refreshTeams();
	}
}
