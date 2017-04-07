package org.cchao.switchbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shucc on 17/4/6.
 * cc@cchao.org
 */
public class SwitchButton extends View {

    private final String TAG = getClass().getName();

    //选中时背景色
    private int selectedColor;

    //未选中时背景色
    private int unSelectedColor;

    //选择按钮颜色
    private int buttonColor;

    //选择按钮padding
    private int padding;

    //当前是否选中
    private boolean checking = false;

    //是否滑动中
    private boolean isScrolling = false;

    private OnSwitchChangeListener onSwitchChangeListener;

    private Paint paint;
    private RectF rectF;

    private int width;
    private int height;

    //按钮半径
    private float buttonRadius;

    //按钮圆心距离左侧距离
    private float currentX = 0;

    //按钮圆心距离左侧最小距离
    private float minCurrentX;

    //按钮圆心距离左侧最大距离
    private float maxCurrentX;

    //自动滑动时每次移动距离
    private float eachScroll;

    //是否向右自动滑动
    private boolean toRight;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitchButton, defStyleAttr, 0);
        selectedColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_selectedColor, Color.RED);
        unSelectedColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_unSelectedColor, Color.GRAY);
        buttonColor = typedArray.getColor(R.styleable.SwitchButton_switchButton_color, Color.WHITE);
        padding = typedArray.getDimensionPixelSize(R.styleable.SwitchButton_switchButton_padding
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width == 0) {
            width = getWidth();
            height = getHeight();
            rectF = new RectF(0, 0, width, height);
            buttonRadius = height - 2 * padding >> 1;
            minCurrentX = buttonRadius + padding;
            maxCurrentX = width - buttonRadius - padding;
        }
        paint.setColor(checking ? selectedColor : unSelectedColor);
        canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);
        float x = minCurrentX;
        if (currentX >= x) {
            x = currentX;
        }
        if (x >= maxCurrentX) {
            x = maxCurrentX;
        }
        if (x == maxCurrentX) {
            paint.setColor(selectedColor);
            canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);
        } else if (x == minCurrentX) {
            paint.setColor(unSelectedColor);
            canvas.drawRoundRect(rectF, height >> 1, height >> 1, paint);
        }
        paint.setColor(buttonColor);
        canvas.drawCircle(x, minCurrentX, buttonRadius, paint);

        if (isScrolling) {
            if (toRight) {
                currentX += eachScroll;
            } else {
                currentX -= eachScroll;
            }
            if (currentX <= minCurrentX) {
                currentX = minCurrentX;
                isScrolling = false;
            }
            if (currentX >= maxCurrentX) {
                currentX = maxCurrentX;
                isScrolling = false;
            }
            invalidate();
        } else {
            if (checking && x <= minCurrentX) {
                checking = false;
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange(false);
                }
            } else if (!checking && x >= maxCurrentX) {
                checking = true;
                if (onSwitchChangeListener != null) {
                    onSwitchChangeListener.onChange(true);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                if (currentX <= minCurrentX || currentX >= maxCurrentX) {
                    return false;
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (currentX <= minCurrentX || currentX >= maxCurrentX) {
                    return false;
                } else {
                    isScrolling = true;
                    reset();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        toRight = (currentX >= width / 2);
        if (toRight) {
            eachScroll = (maxCurrentX - currentX) / 10;
        } else {
            eachScroll = (currentX - minCurrentX) / 10;
        }
        postInvalidate();
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    public boolean isChecked() {
        return checking;
    }

    public void setChecked(boolean check) {
        if (checking == check) {
            return;
        }
        isScrolling = true;
        toRight = check;
        eachScroll = (maxCurrentX - minCurrentX) / 20;
        if (check) {
            currentX = minCurrentX;
        } else {
            currentX = maxCurrentX;
        }
        postInvalidate();
    }

    public void toggle() {
        setChecked(!checking);
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getUnSelectedColor() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public interface OnSwitchChangeListener {
        void onChange(boolean isSelect);
    }
}
