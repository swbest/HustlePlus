import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ProjectService } from '../project.service';
import { Project } from '../project';

@Component({
  selector: 'app-view-my-projects',
  templateUrl: './view-my-projects.page.html',
  styleUrls: ['./view-my-projects.page.scss'],
})
export class ViewMyProjectsPage implements OnInit {

  
	projects: Project[];
  errorMessage: string;
	searchQuery: string = '';

	constructor(private router: Router, private projectService: ProjectService, private sessionService: SessionService) { }

	ngOnInit() {
		this.refreshProjects();
	}

	ionViewWillEnter() {
		this.refreshProjects();
	}

	refreshProjects() {
		this.projectService.getProjectsByStudentId(this.sessionService.getCurrentStudent().userId).subscribe(
			response => {
				this.projects = response.projects
			},
			error => {
				this.errorMessage = error
			}
		);
	}

	getSearchProjects(ev: any) {
		// Reset items back to all of the items
		//		this.refreshStudents();

		// set val to the value of the searchbar
		const val = ev.target.value;

		// if the value is an empty string don't filter the items
		if (val && val.trim() != '') {
			this.projects = this.projects.filter((project) => {
				return (project.projectName.toLowerCase().indexOf(val.toLowerCase()) > -1);
			})
		}
	}

	onCancel(ev: any) {
		// Reset items back to all of the items
		this.refreshProjects();
	}

}
