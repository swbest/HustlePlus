import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ProjectService } from '../project.service';
import { SessionService } from '../session.service';
import { Project } from '../project';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.page.html',
  styleUrls: ['./projects.page.scss'],
})
export class ProjectsPage implements OnInit {

	projects: Project[];
	myProjects: Project[];
	errorMessage: string;
	searchQuery: string = '';

	constructor(private router: Router, private projectService: ProjectService, private sessionService: SessionService) { }

	ngOnInit() {
		this.refreshProjects();
		this.refreshMyProjects();
	}

	ionViewWillEnter() {
		this.refreshProjects();
		this.refreshMyProjects();
	}

	viewProjectDetails(event, project) {
		this.router.navigate(["/viewProjectDetails/" + project.projectId]);
	}

	viewMyProjectDetails(event, project) {
		this.router.navigate(["/viewMyProjectDetails/" + project.projectId]);
	}

	refreshProjects() {
		this.projectService.getProjects().subscribe(
			response => {
				this.projects = response.projects
			},
			error => {
				this.errorMessage = error
			}
		);
	}

	refreshMyProjects() {
		this.projectService.getProjectsByStudentId(this.sessionService.getCurrentStudent().userId).subscribe(
			response => {
				this.myProjects = response.projects
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

	getSearchMyProjects(ev: any) {
		// Reset items back to all of the items
		//		this.refreshStudents();

		// set val to the value of the searchbar
		const val = ev.target.value;

		// if the value is an empty string don't filter the items
		if (val && val.trim() != '') {
			this.myProjects = this.myProjects.filter((project) => {
				return (project.projectName.toLowerCase().indexOf(val.toLowerCase()) > -1);
			})
		}
	}
	
	onCancel(ev: any) {
		// Reset items back to all of the items
		this.refreshProjects();
	}
}
