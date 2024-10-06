import React from 'react'
import { Modal } from '../../../../components/Modal/Modal'
import './RegisterModal.css'
import { RegistrationStep } from '../RegistrationStep/RegistrationStep'


export const RegisterModal:React.FC = () => {
  return (
    <div className="register-modal">
      <Modal>
        <div className='register-container'>
          <RegistrationStep step={1}/>
        </div>
      </Modal>
    </div>
  )
}
