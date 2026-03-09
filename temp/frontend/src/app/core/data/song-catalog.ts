export interface Song {
  id: number;
  title: string;
  artist: string;
}

export const SONG_CATALOG: Song[] = [
  { id: 1, title: 'back in chicago', artist: 'Satish' },
  { id: 2, title: 'diet mountain dew', artist: 'Srinivas' },
  { id: 3, title: 'Ben 10', artist: 'Tarun' }
];

export function getSongById(songId: number): Song | undefined {
  return SONG_CATALOG.find(song => song.id === songId);
}
