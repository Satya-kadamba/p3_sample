import { of, throwError } from 'rxjs';

import { MusicPlayerComponent } from './music-player.component';
import { SongService, Song } from 'src/app/core/service/song.service';
import { ListeningHistoryService } from 'src/app/core/service/listening-history.service';

class AudioMock {
  currentTime = 0;
  duration = 0;
  volume = 1;
  src = '';

  load = jasmine.createSpy('load');
  play = jasmine.createSpy('play').and.returnValue(Promise.resolve());
  pause = jasmine.createSpy('pause');

  addEventListener(): void {}
  removeEventListener(): void {}
}

describe('MusicPlayerComponent (simple Jasmine tests)', () => {
  let component: MusicPlayerComponent;
  let songServiceSpy: jasmine.SpyObj<SongService>;
  let listeningHistorySpy: jasmine.SpyObj<ListeningHistoryService>;
  let audioMock: AudioMock;
  let querySongId: string | null;

  const songs: Song[] = [
    { id: 10, title: 'Song One', url: '/song-1.mp3' },
    { id: 20, title: 'Song Two', url: '/song-2.mp3' }
  ];

  function createComponent(): MusicPlayerComponent {
    const routeStub = {
      snapshot: {
        queryParamMap: {
          get: (key: string) => (key === 'songId' ? querySongId : null)
        }
      }
    };

    return new MusicPlayerComponent(songServiceSpy, listeningHistorySpy, routeStub as any);
  }

  beforeEach(() => {
    songServiceSpy = jasmine.createSpyObj<SongService>('SongService', ['getAllSongs']);
    listeningHistorySpy = jasmine.createSpyObj<ListeningHistoryService>('ListeningHistoryService', ['recordPlay']);
    songServiceSpy.getAllSongs.and.returnValue(of(songs));
    listeningHistorySpy.recordPlay.and.returnValue(of({ success: true, message: 'ok' }));

    querySongId = null;
    audioMock = new AudioMock();
    spyOn(window as any, 'Audio').and.returnValue(audioMock);

    component = createComponent();
  });

  it('should create and load first song on init', () => {
    component.ngOnInit();

    expect(component).toBeTruthy();
    expect(component.songs.length).toBe(2);
    expect(component.currentSongIndex).toBe(0);
    expect(audioMock.src).toBe('/song-1.mp3');
    expect(audioMock.load).toHaveBeenCalled();
  });

  it('should autoplay selected song from query param', async () => {
    querySongId = '20';
    component = createComponent();

    component.ngOnInit();
    await Promise.resolve();

    expect(component.currentSongIndex).toBe(1);
    expect(audioMock.play).toHaveBeenCalled();
    expect(component.isPlaying).toBeTrue();
    expect(listeningHistorySpy.recordPlay).toHaveBeenCalledWith(20);
  });

  it('should show load error when songs fail to load', () => {
    songServiceSpy.getAllSongs.and.returnValue(throwError(() => new Error('network')));
    component = createComponent();

    component.ngOnInit();

    expect(component.songs).toEqual([]);
    expect(component.loadError).toBe('Unable to load songs. Please try again.');
  });

  it('should pause when playPause is pressed while playing', () => {
    component.ngOnInit();
    component.isPlaying = true;

    component.playPause();

    expect(audioMock.pause).toHaveBeenCalled();
    expect(component.isPlaying).toBeFalse();
  });
});
