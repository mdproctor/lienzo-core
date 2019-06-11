package org.roger600.lienzo.client;

import java.util.ArrayList;

import com.ait.lienzo.client.core.image.SpriteLoadedHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Sprite;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.SpriteBehaviorMap;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragMode;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.tools.client.Timer;

public class SpriteExample extends BaseExample implements Example
{
    private final ArrayList<Sprite> m_splist = new ArrayList<>();

    private boolean           m_active = false;

    public SpriteExample(final String title)
    {
        super(title);
    }

    @Override
    public void run()
    {
        addSprite(50, 50, layer);

        addSprite(100, 200, layer);

        addSprite(200, 50, layer);

        addSprite(300, 300, layer);

        addSprite(400, 100, layer);
    }

    @Override
    public void onResize()
    {
        super.onResize();

        setLocation();

        layer.batch();
    }

    private void setLocation()
    {
//        int x = width  / 2;
//
//        for (int j = 0; j < shapes.length; j++) {
//            final Shape shape = shapes[j];
//            shape.setX(x);
//        }
    }

    private final void addSprite(final int x, final int y, final Layer layer)
    {
        final ArrayList<BoundingBox> frames = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            frames.add(BoundingBox.fromDoubles(i * 50, 0, (i * 50) + 50, 50));
        }
        final double tickssec = 10; // ticks per second

        final String behavior = "spincoin";

        final Sprite sprite = new Sprite("coinsprite.png", tickssec, new SpriteBehaviorMap(behavior, frames.toArray( new BoundingBox[frames.size()])), behavior)
                .setDraggable(true).setDragMode(DragMode.SAME_LAYER).setX(x).setY(y)
                .onLoaded(new SpriteLoadedHandler()
        {
            @Override
            public void onSpriteLoaded(final Sprite sprite)
            {
                layer.add(sprite);

                if (m_active)
                {
                    sprite.play();
                }
            }
        });
        m_splist.add(sprite);
    }

    public boolean activate()
    {
        if (!m_active)
        {
            for (final Sprite sprite : m_splist)
            {
                if ((null != sprite.getLayer()) && (sprite.isLoaded()) && (false == sprite.isPlaying()))
                {
                    sprite.play();
                }
            }
            m_active = true;
            return true;
        }
        return false;
    }

    public boolean suspend()
    {
        if (m_active)
        {
            for (final Sprite sprite : m_splist)
            {
                if ((null != sprite.getLayer()) && (sprite.isLoaded()) && (sprite.isPlaying()))
                {
                    sprite.pause();
                }
            }
            m_active = false;
            return true;
        }
        return false;
    }
}
