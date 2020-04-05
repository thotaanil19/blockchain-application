import { Component, OnInit, Output, Input } from '@angular/core';
import { Block } from '../dto/Block';
import { BlockchainService } from 'src/app/services/blockchain.service';
import { Registration } from '../dto/Registration';
import { AdvertisementsService } from 'src/app/services/advertisements.service';
import { Advertisement } from '../dto/Advertisement';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-add.advertisements',
  templateUrl: './add.advertisements.component.html',
  styleUrls: ['./add.advertisements.component.css']
})
export class AddAdvertisementsComponent implements OnInit {

  advertisements: Advertisement[];

  addAdvertisementForm: FormGroup;

  @Input()
  @Output()
  sellerName: string;

  @Input()
  @Output()
  sellerSocialInsuranceNumber: string;

  @Input()
  @Output()
  sellerPhone: string;

  @Input()
  @Output()
  price: number;

  @Input()
  @Output()
  surveyNumber: string;

  @Input()
  @Output()
  propertyAddressState: string = null;   

  @Input()
  @Output()
  propertyAddressCity: string = null;  

  @Input()
  @Output()
  propertyType: string = null;

  submitted = false;
  @Output()
  saveStatus: boolean = false;
  @Output()
  errorMessage: string;

  constructor(private advertisementsService: AdvertisementsService,
    private formBuilder: FormBuilder) {
  }

  ngOnInit() {

    this.addAdvertisementForm = this.formBuilder.group({
      sellerName: ['', Validators.required],
      sellerSocialInsuranceNumber: ['', Validators.required],
      sellerPhone: ['', Validators.required],
      price: ['', Validators.required],
      surveyNumber: ['', Validators.required],
      propertyType: ['', Validators.required],
      propertyAddressCity: ['', Validators.required],
      propertyAddressState: ['', Validators.required]
    });

  }

  get f() { return this.addAdvertisementForm.controls; }

  save(): void {
    let advertisement = {
      sellerName: this.sellerName,
      sellerSocialInsuranceNumber: this.sellerSocialInsuranceNumber,
      sellerPhone: this.sellerPhone,
      price: this.price,
      surveyNumber: this.surveyNumber,
      propertyType: this.propertyType,
      propertyAddressCity: this.propertyAddressCity,
      propertyAddressState: this.propertyAddressState,
      active: true
    };
   

    this.submitted = true;

    // stop here if form is invalid
    if (this.addAdvertisementForm.invalid) {
      return;
    }
    this.advertisementsService.save(advertisement).subscribe(data => {
      if (data) {
        this.cancel();
      }
      this.saveStatus = data;
    }, error => {
      this.errorMessage = error.error;
      this.saveStatus = false;
    });
  }


  cancel(): void {
    this.sellerName = null;
    this.sellerPhone = null; 
    this.surveyNumber = null; 
    this.price = null;
    this.propertyType = null;
    this.propertyAddressCity = null;
    this.propertyAddressState = null;
    this.submitted = false;
    this.errorMessage = null;
    this.addAdvertisementForm.reset();
  }

  populteDistricts (): any[] {
    let cities: any[];
     this.india_states.forEach(e => {
       if (e.name === this.propertyAddressState) {
         cities = e.cities;
       }
     });
     return cities;
   }
 
   india_states: any[] = [    
        {  
           "name":"Andhra Pradesh",
           "cities":[      
             "Adilabad",
             "Bhadradri Kothagudem",
             "Hyderabad",
             "Jagtial",
             "Jangaon",
             "Jayashankar Bhupalpally",
             "Jogulamba Gadwal",
             "Kamareddy",
             "Karimnagar",
             "Khammam",
             "Kumuram Bheem",
             "Mahabubabad",
             "Mahabubnagar",
             "Mancherial",
             "Medak",
             "Medchal",
             "Mulugu",
             "Nagarkurnool",
             "Nalgonda",
             "Narayanpet",
             "Nirmal",
             "Nizamabad",
             "Peddapalli",
             "Rajanna Sircilla",
             "Rangareddy",
             "Sangareddy",
             "Siddipet",
             "Suryapet",
             "Vikarabad",
             "Wanaparthy",
             "Warangal (Rural)",
             "Warangal (Urban)",
             "Yadadri Bhuvanagiri"          
           ]
           }        
       ];
       
     canada_states: any[] = [
     {
     "name": "Alberta",
     "abbreviation": "AB",
     "cities": [
       "Banff",
       "Brooks",
       "Calgary",
       "Edmonton",
       "Fort McMurray",
       "Grande Prairie",
       "Jasper",
       "Lake Louise",
       "Lethbridge",
       "Medicine Hat",
       "Red Deer",
       "Saint Albert"
     ]
   },
   {
     "name": "British Columbia",
     "abbreviation": "BC",
     "cities": [
       "British Columbia",
       "Barkerville",
       "Burnaby",
       "Campbell River",
       "Chilliwack",
       "Courtenay",
       "Cranbrook",
       "Dawson Creek",
       "Delta",
       "Esquimalt",
       "Fort Saint James",
       "Fort Saint John",
       "Hope",
       "Kamloops",
       "Kelowna",
       "Kimberley",
       "Kitimat",
       "Langley",
       "Nanaimo",
       "Nelson",
       "New Westminster",
       "North Vancouver",
       "Oak Bay",
       "Penticton",
       "Powell River",
       "Prince George",
       "Prince Rupert",
       "Quesnel",
       "Revelstoke",
       "Rossland",
       "Trail",
       "Vancouver",
       "Vernon",
       "Victoria",
       "West Vancouver",
       "White Rock"
     ]
   },
   {
     "name": "Manitoba",
     "abbreviation": "MB",
     "cities": [
       "Brandon",
       "Churchill",
       "Dauphin",
       "Flin Flon",
       "Kildonan",
       "Saint Boniface",
       "Swan River",
       "Thompson",
       "Winnipeg",
       "York Factory"
     ]
   },
   {
     "name": "New Brunswick",
     "abbreviation": "NB",
     "cities": [
       "Bathurst",
       "Caraquet",
       "Dalhousie",
       "Fredericton",
       "Miramichi",
       "Moncton",
       "Saint John"
     ]
   },
   {
     "name": "Newfoundland and Labrador",
     "abbreviation": "NL",
     "cities": [
       "Argentia",
       "Bonavista",
       "Channel-Port aux Basques",
       "Corner Brook",
       "Ferryland",
       "Gander",
       "Grand Falls–Windsor",
       "Happy Valley–Goose Bay",
       "Harbour Grace",
       "Labrador City",
       "Placentia",
       "Saint Anthony",
       "St. John’s",
       "Wabana"
     ]
   },
   {
     "name": "Northwest Territories",
     "abbreviation": "NT",
     "cities": [
       "Fort Smith",
       "Hay River",
       "Inuvik",
       "Tuktoyaktuk",
       "Yellowknife"
     ]
   },
   {
     "name": "Nova Scotia",
     "abbreviation": "NS",
     "cities": [
       "Baddeck",
       "Digby",
       "Glace Bay",
       "Halifax",
       "Liverpool",
       "Louisbourg",
       "Lunenburg",
       "Pictou",
       "Port Hawkesbury",
       "Springhill",
       "Sydney",
       "Yarmouth"
     ]
   },
   {
     "name": "Nunavut",
     "abbreviation": "NU",
     "cities": [
       "Iqaluit"
     ]
   },
   {
     "name": "Ontario",
     "abbreviation": "ON",
     "cities": [
       "Bancroft",
       "Barrie",
       "Belleville",
       "Brampton",
       "Brantford",
       "Brockville",
       "Burlington",
       "Cambridge",
       "Chatham",
       "Chatham-Kent",
       "Cornwall",
       "Elliot Lake",
       "Etobicoke",
       "Fort Erie",
       "Fort Frances",
       "Gananoque",
       "Guelph",
       "Hamilton",
       "Iroquois Falls",
       "Kapuskasing",
       "Kawartha Lakes",
       "Kenora",
       "Kingston",
       "Kirkland Lake",
       "Kitchener",
       "Laurentian Hills",
       "London",
       "Midland",
       "Mississauga",
       "Moose Factory",
       "Moosonee",
       "Niagara Falls",
       "Niagara-on-the-Lake",
       "North Bay",
       "North York",
       "Oakville",
       "Orillia",
       "Oshawa",
       "Ottawa",
       "Parry Sound",
       "Perth",
       "Peterborough",
       "Picton",
       "Port Colborne",
       "Saint Catharines",
       "Saint Thomas",
       "Sarnia-Clearwater",
       "Sault Sainte Marie",
       "Scarborough",
       "Simcoe",
       "Stratford",
       "Sudbury",
       "Temiskaming Shores",
       "Thorold",
       "Thunder Bay",
       "Timmins",
       "Toronto",
       "Trenton",
       "Waterloo",
       "Welland",
       "West Nipissing",
       "Windsor",
       "Woodstock",
       "York"
     ]
   },
   {
     "name": "Prince Edward Island",
     "abbreviation": "PE",
     "cities": [
       "Borden",
       "Cavendish",
       "Charlottetown",
       "Souris",
       "Summerside"
     ]
   },
   {
     "name": "Quebec",
     "abbreviation": "QC",
     "cities": [
       "Asbestos",
       "Baie-Comeau",
       "Beloeil",
       "Cap-de-la-Madeleine",
       "Chambly",
       "Charlesbourg",
       "Châteauguay",
       "Chibougamau",
       "Côte-Saint-Luc",
       "Dorval",
       "Gaspé",
       "Gatineau",
       "Granby",
       "Havre-Saint-Pierre",
       "Hull",
       "Jonquière",
       "Kuujjuaq",
       "La Salle",
       "La Tuque",
       "Lachine",
       "Laval",
       "Lévis",
       "Longueuil",
       "Magog",
       "Matane",
       "Montreal",
       "Montréal-Nord",
       "Percé",
       "Port-Cartier",
       "Quebec",
       "Rimouski",
       "Rouyn-Noranda",
       "Saguenay",
       "Saint-Eustache",
       "Saint-Hubert",
       "Sainte-Anne-de-Beaupré",
       "Sainte-Foy",
       "Sainte-Thérèse",
       "Sept-Îles",
       "Sherbrooke",
       "Sorel-Tracy",
       "Trois-Rivières",
       "Val-d’Or",
       "Waskaganish"
     ]
   },
   {
     "name": "Saskatchewan",
     "abbreviation": "SK",
     "cities": [
       "Batoche",
       "Cumberland House",
       "Estevan",
       "Flin Flon",
       "Moose Jaw",
       "Prince Albert",
       "Regina",
       "Saskatoon",
       "Uranium City"
     ]
   },
   {
     "name": "Yukon Territory",
     "abbreviation": "YT",
     "cities": [
       "Dawson",
       "Watson Lake",
       "Whitehorse"
     ]
   }
 ];


}
