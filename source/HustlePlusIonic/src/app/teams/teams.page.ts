import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from '../session.service';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { TeamService } from '../team.service';
import { Team } from '../team';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.page.html',
  styleUrls: ['./teams.page.scss'],
})
export class TeamsPage implements OnInit {

  students: Student[];
  teams: Team[];
  errorMessage: string;
  searchQuery: string = '';
  retrieveTeamsError: boolean;

  constructor(private router: Router,
    private studentService: StudentService,
    private teamService: TeamService,
    private sessionService: SessionService) { }

  ngOnInit() {
    this.refreshStudents();
    this.refreshTeams();
  }

  ionViewWillEnter() {
    this.refreshStudents();
    this.refreshTeams();
  }

  viewStudentDetails(event, student) {
    this.router.navigate(["/viewStudentDetails/" + student.userId]);
  }

  refreshStudents() {
    this.studentService.getAllStudents().subscribe(
      response => {
        this.students = response.students
      },
      error => {
        this.errorMessage = error
      }
    );
  }

  getSearchStudents(ev: any) {
    // Reset items back to all of the items
    //		this.refreshStudents();

    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
      this.students = this.students.filter((student) => {
        return (student.name.toLowerCase().indexOf(val.toLowerCase()) > -1);
        // || student.skills.filter(skill => skill.title.toLowerCase().indexOf(val.toLowerCase()) == 0));
      })
    }
  }

  refreshTeams() {
    this.teamService.getTeamByStudentId(this.sessionService.getCurrentStudent().userId).subscribe(
      response => {
        this.teams = response.teams
      },
      error => {
        this.errorMessage = error
        this.retrieveTeamsError = true
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

  viewTeamDetails(event, team) {
    this.router.navigate(["/viewTeamDetails/" + team.teamId]);
  }

  onCancel(ev: any) {
    // Reset items back to all of the items
    this.refreshStudents();
    this.refreshTeams();
  }

  createNewTeam() {
    this.router.navigate(["/createNewTeam"]);
  }
}
