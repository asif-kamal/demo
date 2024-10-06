import React, { useState } from 'react'
import { Modal } from '../../../../components/Modal/Modal'
import './RegisterModal.css'
import { RegistrationStep } from '../RegistrationStep/RegistrationStep'


export const RegisterModal:React.FC = () => {

  const [step, setStep] = useState<number>(4)

  const stepButtonClicked = () => {
    step === 1 || step === 4 || step >= 6 ? setStep(step) : setStep(step-1)
  }

  return (
    <div className="register-modal">
      <Modal>
        <div className='register-container'>
          <RegistrationStep step={step} changeStep={stepButtonClicked}/>
        </div>
      </Modal>
    </div>
  )
}
