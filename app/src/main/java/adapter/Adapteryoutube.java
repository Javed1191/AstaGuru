package adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

class Adapteryoutube extends FragmentStatePagerAdapter
{



		private String[] getVids()
		{
			return new String[]{"ORgucLTtTDI", "WzQ2RvSLR4o","dQw4w9WgXcQ"};
		}

		public Adapteryoutube(final FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(final int position)
		{
			YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
			fragment.initialize("AIzaSyB_bApGLgdJKI_9HUq2DvSHM5oYijsm5ag", new YouTubePlayer.OnInitializedListener()
			{
				@Override
				public void onInitializationSuccess(final YouTubePlayer.Provider provider,
													final YouTubePlayer youTubePlayer,
													final boolean b)
				{
					youTubePlayer.cueVideo(getVids()[position]);
				}

				@Override
				public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
				{

				}


				});


			return fragment;
		}

	@Override
	public int getCount()
	{
		return getVids().length;
	}

	@Override
	public CharSequence getPageTitle(final int position)
	{
		return getVids()[position] + " [" + position + "]";
	}
}
