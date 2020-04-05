import { Registration } from './Registration';


export interface Block {

    id: string;	
	data: Registration;	
	hash: string;	
	previousHash: string;	
	timeStamp: number;
	registerationDoneBy: string

	
}