import { Modal, Box, Typography, IconButton, Paper } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import type { User } from '../types/user';

interface UserModalProps {
  user: User | null;
  open: boolean;
  onClose: () => void;
}

const UserModal = ({ user, open, onClose }: UserModalProps) => {
  if (!user) return null;

  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="user-modal-title"
      sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Paper
        sx={{
          position: 'relative',
          width: '90%',
          maxWidth: 600,
          maxHeight: '90vh',
          overflow: 'auto',
          p: 4,
          m: 2,
        }}
      >
        <IconButton
          onClick={onClose}
          sx={{
            position: 'absolute',
            right: 8,
            top: 8,
          }}
        >
          <CloseIcon />
        </IconButton>

        <Typography variant="h5" component="h2" gutterBottom>
          {user.name}
        </Typography>

        <Box sx={{ mt: 2 }}>
          <Typography variant="subtitle1" gutterBottom>
            Contact Information
          </Typography>
          <Typography>Email: {user.email}</Typography>
          <Typography>Phone: {user.phone}</Typography>
          <Typography>Website: {user.website}</Typography>
        </Box>

        <Box sx={{ mt: 2 }}>
          <Typography variant="subtitle1" gutterBottom>
            Address
          </Typography>
          <Typography>
            {user.address.street}, {user.address.suite}
          </Typography>
          <Typography>
            {user.address.city}, {user.address.zipcode}
          </Typography>
          <Typography>
            <a
              href={`https://www.google.com/maps?q=${user.address.geo.lat},${user.address.geo.lng}`}
              target="_blank"
              rel="noopener noreferrer"
            >
              View on Map
            </a>
          </Typography>
        </Box>

        <Box sx={{ mt: 2 }}>
          <Typography variant="subtitle1" gutterBottom>
            Company
          </Typography>
          <Typography>Name: {user.company.name}</Typography>
          <Typography>Catch Phrase: {user.company.catchPhrase}</Typography>
          <Typography>Business: {user.company.bs}</Typography>
        </Box>
      </Paper>
    </Modal>
  );
};

export default UserModal; 