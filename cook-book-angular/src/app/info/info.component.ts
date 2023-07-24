import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, UrlTree} from "@angular/router";
import {RecipeResponse} from "../model/RecipeResponse";
import {environment} from "../../environment/environment";
import {HttpClient} from "@angular/common/http";
import {Location} from "@angular/common";
import {RecipeRequest} from "../model/RecipeRequest";
import {RecipeRequestRoot} from "../model/RecipeRequestRoot";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css', '../recipe/recipe.component.css']
})
export class InfoComponent implements OnInit{
  parentId = -1;
  children: RecipeResponse[] = [];
  historyRecipes: RecipeResponse[] = [];
  postRecipe: RecipeRequest = new RecipeRequest();
  updateRecipe: RecipeRequest = new RecipeRequest();
  showForm: boolean = false;
  messageToUser = "";
  showAddNewRipe: boolean = false;
  showUpdateNewRipe: boolean = false;
  showHistory: boolean = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.parentId = params['parentId'];
    })
    this.showChildrenDefault();
  }

  showChildRecipe(id: number) {
    this.router.navigate(['/info'], {queryParams: {parentId: id}});
    this.showAllChildren(id);
  }

  showChildrenDefault() {
    this.showAllChildren(this.parentId);
  }

  showAllChildren(id: number) {
    this.messageToUser = "";
    this.http.get<RecipeResponse[]>(environment.backendURL + "/recipes/child/" + id).subscribe({
      next: ((response: RecipeResponse[]) => {
        this.children = response;
      }),
      error: (error => {
        console.log("Something went wrong ");
        console.log(error)
      })
    })
  }

  getHistory(parentId: number) {
    this.messageToUser = "";
    if (parentId == null) {
      this.messageToUser = "This recipe don't have a history";
      return;
    }
    this.showHistory = !this.showHistory;
    this.http.get<RecipeResponse[]>(environment.backendURL + "/recipes/history/" + parentId).subscribe({
      next: ((response: RecipeResponse[]) => {
        this.historyRecipes = response;
      }),
      error: (error => {
        console.log("Something went wrong ");
        console.log(error)
      })
    })
  }

  addChildToParentRecipe() {
    if (this.checkInput(this.postRecipe)) {
      return;
    }
    const body = JSON.stringify(this.postRecipe);
    this.http.post<RecipeRequest>(environment.backendURL + "/recipes/" + this.parentId + "/children", body, {
      headers: {
        "Content-Type": "application/json"
      }
    }).subscribe({
      next: ((response: RecipeRequest) => {
        this.postRecipe = response;
        this.clearForm();
        this.showForm = false;
        this.showChildrenDefault();
        this.messageToUser = "You successful add a new recipe!"
        this.showAddNewRipe = false;
      }),
      error: (error => {
        console.log(error);
        this.messageToUser = "Something went wrong. Try again"
      })
    })
  }

  goUpdate() {
    if (this.checkInput(this.updateRecipe)) {
      return;
    }
    this.showHistory = false;
    const body = JSON.stringify(this.updateRecipe);
    this.http.post<any>(environment.backendURL + "/recipes/update/" + this.updateRecipe.id, body, {
      headers: {
        "Content-Type": "application/json"
      }
    }).subscribe({
      next: ((response: any) => {
        this.showChildrenDefault();
        this.clearUpdateForm();
        this.messageToUser = "You successful update a recipe"
        this.showUpdateNewRipe = false;
      }),
      error: (error => {
        console.log(error);
        this.messageToUser = "Something went wrong. Try again"
      })
    })
  }

  checkInput(recipe: RecipeRequest): boolean {
    if (recipe.name.length < 5 || recipe.info.length < 15) {
      this.messageToUser = "Name should be longer then 5 characters, info longer then 15 character!";
      return true;
    }
    return false;
  }

  clearUpdateForm() {
    this.updateRecipe = new RecipeRequest();
  }

  clearForm() {
    this.postRecipe = new RecipeRequest();
  }

  reload() {
    location.reload();
  }

  setUpdateRecipe(recipe: RecipeResponse) {
    this.updateRecipe = recipe;
    this.showUpdateNewRipe = !this.showUpdateNewRipe;
  }

  showAddNewRecipeBlock() {
    this.showAddNewRipe = !this.showAddNewRipe;
  }
}
