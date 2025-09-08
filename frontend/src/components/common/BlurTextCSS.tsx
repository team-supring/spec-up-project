import React, {
  useEffect,
  useRef,
  useState,
} from 'react';

interface BlurTextCSSProps {
  text?: string;
  delay?: number;
  className?: string;
  animateBy?: 'words' | 'characters';
  direction?: 'top' | 'bottom';
  onAnimationComplete?: () => void;
}

const BlurTextCSS: React.FC<BlurTextCSSProps> = ({
  text = '',
  delay = 200,
  className = '',
  animateBy = 'words',
  direction = 'top',
  onAnimationComplete,
}) => {
  const elements = animateBy === 'words' ? text.split(' ') : text.split('');
  const [inView, setInView] = useState(false);
  const ref = useRef<HTMLParagraphElement>(null);

  useEffect(() => {
    if (!ref.current) return;
    
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setInView(true);
          observer.unobserve(ref.current!);
        }
      },
      { threshold: 0.1 }
    );
    
    observer.observe(ref.current);
    return () => observer.disconnect();
  }, []);

  useEffect(() => {
    if (inView && onAnimationComplete) {
      const totalDelay = elements.length * delay;
      const timer = setTimeout(onAnimationComplete, totalDelay);
      return () => clearTimeout(timer);
    }
  }, [inView, onAnimationComplete, elements.length, delay]);

  return (
    <p
      ref={ref}
      className={className}
      style={{ display: 'flex', flexWrap: 'wrap' }}
    >
      {elements.map((segment, index) => (
        <span
          key={index}
          className={`inline-block transition-all duration-700 ease-out ${
            inView
              ? 'opacity-100 blur-0 translate-y-0'
              : `opacity-0 blur-md ${
                  direction === 'top' ? '-translate-y-8' : 'translate-y-8'
                }`
          }`}
          style={{
            transitionDelay: `${index * delay}ms`,
          }}
        >
          {segment === ' ' ? '\u00A0' : segment}
          {animateBy === 'words' && index < elements.length - 1 && '\u00A0'}
        </span>
      ))}
    </p>
  );
};

export default BlurTextCSS;


