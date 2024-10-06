import React from 'react'
import { displayIcon } from '../../utils/RegisterStepUtils';

interface RegisterStepProps {
    step: number;
}

export const RegistrationStep:React.FC<RegisterStepProps> = ({step}) => {
  return (
    <div className='reg-step-container'>
        <div className='reg-step-btn'>
      {displayIcon(step)}
      </div>
      <span className='reg-step-number'>Step {step} of 6</span>
    </div>
  )
}
