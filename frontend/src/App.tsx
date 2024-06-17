import {Box, Button, Container, Grid, TextField, Typography} from "@mui/material";
import {Search as SearchIcon} from '@mui/icons-material';
import {useEffect, useState} from "react";
import {DeveloperResponse} from "./types/developer.ts";
import DevelopersTable from "./components/DevelopersTable.tsx";
import {styles} from "./styles.ts";
import {fetchAllDevelopers, fetchDevelopersByLanguage} from "./api/developersApi.ts";


const App: React.FC = () => {
    const [searchQuery, setSearchQuery] = useState<string>('');
    const [developers, setDevelopers] = useState<DeveloperResponse[]>([]);

    const handleSearch = () => {
        if (searchQuery.trim() === '') {
            fetchAllDevelopers().then(developersArr => setDevelopers(developersArr));
        } else {
            fetchDevelopersByLanguage(searchQuery).then(developersArr => setDevelopers(developersArr));
        }
    };

    useEffect(() => {
        // Load all developers initially
        fetchAllDevelopers().then(developersArr => setDevelopers(developersArr));
    }, []);

    useEffect(() => {
        // Perform search whenever searchQuery changes
        handleSearch();
    }, [searchQuery]);
    return (
        <Container sx={{padding: 5}}>
            <Typography variant="h5" gutterBottom>
                GithubCrawler Version 1.0
            </Typography>
            <Grid container justifyContent="center" alignItems="center" direction="row" mb={5} spacing={2}>

                <Grid container justifyContent="center" alignItems="center" direction="column" spacing={2}>
                    <TextField
                        label="Search by language"
                        variant="outlined"
                        size="small"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                    <Grid item>
                        <Typography variant="body2" style={{fontSize: '10px'}}>
                            {developers.length} developer(s)
                        </Typography>
                    </Grid>
                    <Grid item>
                        <Button variant="outlined" size="small" color="success" startIcon={<SearchIcon/>}
                                onClick={handleSearch}>Search</Button>
                    </Grid>

                </Grid>

            </Grid>
            <DevelopersTable developers={developers}/>
            <Box
                sx={styles.footer}
            >
                <Typography variant="caption" color="#978897">
                    Mouadh Khlifi - 2024
                </Typography>
            </Box>
        </Container>
    );
};

export default App;