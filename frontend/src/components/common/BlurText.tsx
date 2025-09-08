import {
  useEffect,
  useRef,
  useState,
} from 'react';

import { motion } from 'framer-motion';

interface BlurTextProps {
  text?: string;
  delay?: number;
  className?: string;
  animateBy?: 'words' | 'characters';
  direction?: 'top' | 'bottom';
  threshold?: number;
  rootMargin?: string;
  onAnimationComplete?: () => void;
  stepDuration?: number;
}

const BlurText = ({
  text = '',
  delay = 200,
  className = '',
  animateBy = 'words',
  direction = 'top',
  threshold = 0.1,
  rootMargin = '0px',
  onAnimationComplete,
  stepDuration = 0.35,
}: BlurTextProps) => {
  const elements = animateBy === 'words' ? text.split(' ') : text.split('');
  const [inView, setInView] = useState(false);
  const ref = useRef<HTMLParagraphElement>(null);

  useEffect(() => {
    if (!ref.current) return;
    
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setInView(true);
          if (ref.current) {
            observer.unobserve(ref.current);
          }
        }
      },
      { threshold, rootMargin }
    );
    
    observer.observe(ref.current);
    return () => observer.disconnect();
  }, [threshold, rootMargin]);

  // 빈 텍스트 처리
  if (!text.trim()) {
    return <p className={className} />;
  }

  return (
    <p
      ref={ref}
      className={className}
      style={{ display: 'flex', flexWrap: 'wrap' }}
    >
      {elements.map((segment, index) => {
        const initialY = direction === 'top' ? -20 : 20;
        const finalY = 0;

        return (
          <motion.span
            className="inline-block"
            key={`${segment}-${index}`}
            initial={{ 
              opacity: 0, 
              y: initialY,
              filter: 'blur(8px)'
            }}
            animate={inView ? { 
              opacity: 1, 
              y: finalY,
              filter: 'blur(0px)'
            } : { 
              opacity: 0, 
              y: initialY,
              filter: 'blur(8px)'
            }}
            transition={{
              duration: stepDuration,
              delay: (index * delay) / 1000,
              ease: "easeOut"
            }}
            onAnimationComplete={
              index === elements.length - 1 ? onAnimationComplete : undefined
            }
          >
            {segment === ' ' ? '\u00A0' : segment}
            {animateBy === 'words' && index < elements.length - 1 && '\u00A0'}
          </motion.span>
        );
      })}
    </p>
  );
};

export default BlurText;
